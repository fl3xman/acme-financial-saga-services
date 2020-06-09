import * as React from "react";

import { Route, Switch } from "react-router";
import { InversifyProvider, JoinPointProps } from "acme-commons";

import { JoinPointRouter } from "./components/router";
import { OrderCreate } from "./components/pages/order-create";
import { OrderList } from "./components/pages/order-list";

export const Payment: React.FC<JoinPointProps> = ({ id, history, container }: JoinPointProps) => {
    return (
        <InversifyProvider id={id} container={container}>
            <JoinPointRouter history={history}>
                <Switch>
                    <Route exact path="/payments/create" component={OrderCreate} />
                    <Route exact path="/payments" component={OrderList} />
                </Switch>
            </JoinPointRouter>
        </InversifyProvider>
    );
};
