import * as React from "react";
import { Router, Switch } from "react-router";
import { InversifyProvider, JoinPointProps } from "acme-commons";

export const Payment: React.FC<JoinPointProps> = ({ id, history, container }: JoinPointProps) => {
    return (
        <InversifyProvider id={id} container={container}>
            <Router history={history}>
                <div>Payment</div>
            </Router>
        </InversifyProvider>
    );
};