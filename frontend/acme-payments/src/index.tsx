import * as React from "react";
import * as ReactDOM from "react-dom";

import { useJoinPoint } from "acme-commons";

import { Payment } from "./Payment";
import { PaymentJoinId } from "./PaymentJoinId";

useJoinPoint(PaymentJoinId.Namespace, PaymentJoinId.Name).mount = (id, history, container) => {
    ReactDOM.render(<Payment id={id} history={history} container={container} />, document.getElementById(id));
    // registerServiceWorker();
};

useJoinPoint(PaymentJoinId.Namespace, PaymentJoinId.Name).unmount = (id) => {
    ReactDOM.unmountComponentAtNode(document.getElementById(id));
};
