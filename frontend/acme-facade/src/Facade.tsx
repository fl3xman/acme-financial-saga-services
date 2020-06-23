import * as React from "react";

import { Container } from "inversify";
import { Route, Switch, RouterProps } from "react-router";
import { HashRouter } from "react-router-dom";
import { InversifyProvider, Mediator } from "acme-commons";

import { configuration as LoggerConfiguration } from "./configuration/LoggerConfiguration";
import { configuration as MediatorConfiguration } from "./configuration/MediatorConfiguration";
import { configuration as ReduxConfiguration } from "./configuration/ReduxConfiguration";

import { ReduxProvider } from "./contracts";
import { Dashboard } from "./components/screen/dashboard";

const container = new Container();

const Payment = ({ history }: RouterProps) => <Mediator history={history} namespace="acme" name="payment" host="http://localhost:4001" />;
const Account = ({ history }: RouterProps) => <Mediator history={history} namespace="acme" name="account" host="http://localhost:4002" />;

export const Facade: React.FC = () => {
    return (
        <InversifyProvider container={container} configurations={[LoggerConfiguration(), MediatorConfiguration(), ReduxConfiguration()]}>
            <ReduxProvider>
                <HashRouter>
                    <Switch>
                        <Route exact path="/" component={Dashboard} />
                        <Route path="/payments" component={Payment} />
                        <Route path="/account" component={Account} />
                    </Switch>
                </HashRouter>
            </ReduxProvider>
        </InversifyProvider>
    );
};
