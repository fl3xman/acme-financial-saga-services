import * as React from "react";
import * as _ from "lodash";

import { createStore } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import { Provider } from "react-redux";
import { useReduxRegistry } from "acme-commons";

import { PaymentActionType } from "./PaymentActionType";
import { paymentReducer } from "./PaymentReducer";

export const ContractProvider: React.FC = (props: React.PropsWithChildren<unknown>) => {
    const registry = useReduxRegistry();
    registry?.add(PaymentActionType.Name, paymentReducer);

    if (!_.isNil(registry)) {
        return <>{props.children}</>;
    }

    const store = createStore(paymentReducer, composeWithDevTools());
    return <Provider store={store}>{props.children}</Provider>;
};
