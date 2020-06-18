import * as React from "react";

import { createStore } from "redux";
import { Provider } from "react-redux";
import { useReduxRegistry } from "acme-commons";

import { PaymentType } from "./PaymentType";
import { paymentReducer } from "./PaymentContract";

export const PaymentContractProvider: React.FC<{ mounted: boolean }> = (props: React.PropsWithChildren<{ mounted: boolean }>) => {
    const registry = useReduxRegistry();
    registry.add(PaymentType.Name, paymentReducer);

    if (props.mounted) {
        return <>{props.children}</>;
    }

    return <Provider store={createStore(registry.combinedReducers)}>{props.children}</Provider>;
};
