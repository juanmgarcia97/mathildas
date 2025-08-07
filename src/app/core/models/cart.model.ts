export interface ProductCartItem {
    productId: number;
    userId: number;
}

export interface ProductCartItemQuantity {
    productId: number;
    userId: number;
    quantity: number;
}

export interface CartItem {
    id: number;
    userId: number;
    orderId: number;
    productId: number;
    productName: string;
    price: number;
    productImage: string;
    quantity: number;
}

export interface Order {
    id: number;
    date: string;
    amount: number;
    payment: string;
    address: string;
    description: string | null;
    city: string;
    state: string;
    userId: number;
    totalAmount: number;
    discount: number;
    status: OrderStatus;
    trackingId: string;
    userName: string;
    couponCode: string;
    cartItems: CartItem[];
}

export type PlaceOrder = Pick<Order, 'userId' | 'address' | 'description'>;

export enum OrderStatus {
    PENDING = 'PENDING',
    PLACED = 'PLACED',
    SHIPPED = 'SHIPPED',
    DELIVERED = 'DELIVERED'
}
