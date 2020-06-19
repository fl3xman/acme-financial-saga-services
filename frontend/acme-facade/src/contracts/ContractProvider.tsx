import * as React from "react";

import { Store } from "redux";
import { Provider } from "react-redux";
import { useInversify, useReduxRegistry } from "acme-commons";

import { ReduxStoreAssembly } from "../commons/redux";
import { DashboardType, dashboardReducer } from "./dashboard";

export const ContractProvider: React.FC<unknown> = (props: React.PropsWithChildren<unknown>) => {
    const container = useInversify();
    const registry = useReduxRegistry();
    const store = container.get<Store>(ReduxStoreAssembly.Instance);

    registry?.add(DashboardType.Name, dashboardReducer);

    return <Provider store={store}>{props.children}</Provider>;
};
