import "reflect-metadata";

import * as React from "react";
import * as ReactDOM from "react-dom";
import * as ProcessENV from "dotenv";

import { Facade } from "./Facade";

ProcessENV.config();
ReactDOM.render(<Facade />, document.getElementById("root"));
