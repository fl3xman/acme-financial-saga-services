import { combineReducers } from "redux";
import { OrderType, orderReducer } from "./orders";

export const rootReducer = combineReducers({
    [OrderType.Name]: orderReducer,
});
