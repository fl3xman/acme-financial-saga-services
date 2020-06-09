import * as React from "react";

import { Container } from "inversify";
import { Route, Switch, RouterProps } from "react-router";
import { HashRouter } from "react-router-dom";
import { InversifyProvider, Mediator } from "acme-commons";

import { configuration as LoggerConfiguration } from "./configuration/LoggerConfiguration";
import { configuration as MediatorConfiguration } from "./configuration/MediatorConfiguration";

const container = new Container();

const Dashboard = () => <div>Dashboard</div>;
const Payment = ({ history }: RouterProps) => <Mediator history={history} namespace="acme" name="payment" host="http://localhost:4001" />;

export const Facade: React.FC = () => {
    return (
        <InversifyProvider container={container} configurations={[LoggerConfiguration(), MediatorConfiguration()]}>
            <HashRouter>
                <Switch>
                    <Route exact path="/" component={Dashboard} />
                    <Route path="/payments" component={Payment} />
                </Switch>
            </HashRouter>
        </InversifyProvider>
    );
};
