import { Action } from "redux";

import { DashboardActionType } from "./DashboardActionType";
import { DashboardState } from "./DashboardState";

export const dashboardReducer = (state = { initialized: false }, action: Action<DashboardActionType>): DashboardState => {
    switch (action.type) {
        case DashboardActionType.Init:
            return { initialized: true };
        default:
            return state;
    }
};
