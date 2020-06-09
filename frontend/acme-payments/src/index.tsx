import * as H from "history";
import * as React from "react";
import * as ReactDOM from "react-dom";

import { interfaces } from "inversify";
import { useJoinPoint, guard } from "acme-commons";

import { Payment } from "./Payment";

useJoinPoint("acme", "payment", {
    mount: (id: string, history: H.History, container: interfaces.Container) => {
        ReactDOM.render(<Payment id={id} history={history} container={container} />, document.getElementById(id));
    },
    unmount: (id) => {
        guard(document.getElementById(id), (safe) => ReactDOM.unmountComponentAtNode(safe));
    },
});
