import * as _ from "lodash";
import * as React from "react";

import { Router, RouterProps } from "react-router";
import { HashRouter } from "react-router-dom";

export const JoinPointRouter: React.FC<Partial<RouterProps>> = (props: React.PropsWithChildren<Partial<RouterProps>>) => {
    if (_.isNil(props.history)) {
        return <HashRouter>{props.children}</HashRouter>;
    }
    return <Router history={props.history}>{props.children}</Router>;
};
