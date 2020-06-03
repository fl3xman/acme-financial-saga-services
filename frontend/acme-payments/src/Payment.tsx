import * as React from "react";

import { InversifyProvider, JoinPointProps } from "acme-commons";
import { JoinPointRouter } from "./components/router";

export const Payment: React.FC<JoinPointProps> = ({ id, history, container }: JoinPointProps) => {
    return (
        <InversifyProvider id={id} container={container}>
            <JoinPointRouter history={history}>
                <div>Payment</div>
            </JoinPointRouter>
        </InversifyProvider>
    );
};
