import * as React from "react";
import * as _ from "lodash";

import { Route, Switch } from "react-router";
import { InversifyProvider, JoinPointProps } from "acme-commons";

import { JoinPointRouter } from "./components/router";
import { OrderCreate } from "./components/screens/order-create";
import { OrderList } from "./components/screens/order-list";

import { ContractProvider } from "./contracts";

export const Payment: React.FC<JoinPointProps> = ({ id, history, container }: JoinPointProps) => {
    return (
        <InversifyProvider id={id} container={container}>
            <ContractProvider joined={!_.isNil(container)}>
                <JoinPointRouter history={history}>
                    <Switch>
                        <Route exact path="/payments/create" component={OrderCreate} />
                        <Route exact path="/payments" component={OrderList} />
                    </Switch>
                </JoinPointRouter>
            </ContractProvider>
        </InversifyProvider>
    );
};
