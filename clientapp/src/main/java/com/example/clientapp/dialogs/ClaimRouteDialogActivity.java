package com.example.clientapp.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientapp.GameActivity;
import com.example.clientapp.IClaimRouteView;
import com.example.clientapp.R;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.utils;

import java.util.ArrayList;

import presenters.GamePlayPresenter;
import presenters.IGamePlayPresenter;

/**
 * Created by Daniel on 3/23/2018.
 */

public class ClaimRouteDialogActivity extends Activity implements IClaimRouteView {

//    private IGamePlayPresenter gamePlayPresenter = GamePlayPresenter.getInstance();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_claim_route);
        GamePlayPresenter.getInstance().setClaimRouteView(this);

        mRecyclerView = findViewById(R.id.routes_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RoutesAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startHandFragment(String routeColor, int length, int routeId) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(utils.CLAIMING_ROUTE, utils.CLAIMING_ROUTE);
        intent.putExtra(utils.ROUTE_COLOR, routeColor);
        intent.putExtra(utils.ROUTE_LENGTH, length);
        intent.putExtra(utils.ROUTE_ID, routeId);
        startActivity(intent);
    }


    protected class RoutesHolder extends RecyclerView.ViewHolder{

        private TextView mRouteInfo;
        private Route mRoute;

        public RoutesHolder(View itemView) {
            super(itemView);
            mRouteInfo = itemView.findViewById(R.id.route_info);
            mRouteInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GamePlayPresenter.getInstance().claimRoute(mRoute.getId());
                }
            });
        }

        public void bindRoute(Route route){
            mRouteInfo.setText(createRouteInfo(route));
            mRoute = route;
            if (!route.isAvailable()) {
                mRouteInfo.setPaintFlags(mRouteInfo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mRouteInfo.setTextColor(Color.parseColor("#696969"));
            }
        }

        public String createRouteInfo(Route route) {
            StringBuilder sb = new StringBuilder();

            sb.append(route.getCityOne() + "--" + route.getCityTwo() + "\n" +
                    "LENGTH: " + route.getLength() + " WORTH: " + route.getWorth() + " COLOR: ");
            if (route.getColor().isEmpty()) {
                sb.append("NO COLOR");
            }
            else {
                sb.append(route.getColor());
            }
            return sb.toString();
        }
    }

    protected class RoutesAdapter extends RecyclerView.Adapter<ClaimRouteDialogActivity.RoutesHolder>{
        private ArrayList<Route> mRoutes;

        public RoutesAdapter() {
            mRoutes = combineLists();
        }

        public ArrayList combineLists(){
            ArrayList<Route> routes = RootClientModel.getRoutesList();
            return routes;
        }

        @Override
        public RoutesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ClaimRouteDialogActivity.this);
            View view = layoutInflater.inflate(R.layout.item_route_in_list, parent, false);
            return new RoutesHolder(view);
        }

        @Override
        public void onBindViewHolder(RoutesHolder holder, int position) {
            Route route = mRoutes.get(position);
            holder.bindRoute(route);
        }

        @Override
        public int getItemCount() {
            return mRoutes.size();
        }
    }
}
