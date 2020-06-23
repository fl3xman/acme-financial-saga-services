import * as React from "react";

import { Store } from "redux";
import { Provider } from "react-redux";
import { useInversify, useReduxRegistry } from "acme-commons";

import { ReduxStoreAssembly } from "../commons/redux";
import { DashboardActionType, dashboardReducer } from "./dashboard";

export const ReduxProvider: React.FC<unknown> = (props: React.PropsWithChildren<unknown>) => {
    const container = useInversify();
    const registry = useReduxRegistry();
    registry?.add(DashboardActionType.Name, dashboardReducer);

    return <Provider store={container.get<Store>(ReduxStoreAssembly.Instance)}>{props.children}</Provider>;
};
