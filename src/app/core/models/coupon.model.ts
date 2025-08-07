export interface Coupon {
    id: number;
    name: string;
    code: string;
    discount: number;
    expirationDate: Date;
}

export type CreateCoupon = Omit<Coupon, 'id'>;