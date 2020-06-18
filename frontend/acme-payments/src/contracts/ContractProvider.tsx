import * as React from "react";

import { createStore, combineReducers } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import { Provider } from "react-redux";
import { useReduxRegistry } from "acme-commons";

import { PaymentType } from "./PaymentType";
import { paymentReducer } from "./PaymentContract";

export const ContractProvider: React.FC<{ joined: boolean }> = (props: React.PropsWithChildren<{ joined: boolean }>) => {
    if (props.joined) {
        const registry = useReduxRegistry();
        registry?.add(PaymentType.Name, paymentReducer);

        return <>{props.children}</>;
    }

    const store = createStore(combineReducers({ [PaymentType.Name]: paymentReducer }), composeWithDevTools());
    return <Provider store={store}>{props.children}</Provider>;
};
