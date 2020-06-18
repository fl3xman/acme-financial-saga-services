import { Action } from "redux";

import { DashboardType } from "./DashboardType";
import { DashboardState } from "./DashboardState";

export const dashboardReducer = (state = { initialized: false }, action: Action<DashboardType>): DashboardState => {
    switch (action.type) {
        case DashboardType.Init:
            return { initialized: true };
        default:
            return state;
    }
};
