import * as React from "react";

import { useSelector } from "react-redux";
import { OrderState } from "src/contracts/orders";

export const OrderCreate: React.FC = () => {
    const loading = useSelector<OrderState>((state) => state.loading);
    if (loading) {
        return <div>Payment will load</div>;
    }
    return <div>Payment order create</div>;
};
