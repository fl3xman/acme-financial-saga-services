import { combineReducers } from "redux";
import { OrderType, orderReducer } from "./orders";

export const paymentReducer = combineReducers({
    [OrderType.Name]: orderReducer,
});
