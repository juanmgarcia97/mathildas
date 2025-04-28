export const SLASH = '/'
export const ADMIN_ROUTE = 'admin'
export const CUSTOMER_ROUTE = 'customer'
export const AUTH_ROUTE = 'auth'

/* Shared route */
export const DASHBOARD = 'dashboard'

/* Admin routes */
export const ANALYTICS = 'analytics'
export const CATEGORY = 'category'
export const PRODUCT = 'product'
export const ORDERS = 'orders'
export const POST_COUPON = 'post-coupon'
export const COUPON = 'coupon'
export const FAQ = 'faq'
export const SEARCH = 'search'

/* Customer routes */
export const CART = 'cart'
export const MY_ORDERS = 'my-orders'
export const WHISHLIST = 'whishlist'
export const PROFILE = 'profile'

/* Visitor routes */
export const ORDER = 'order'
export const SIGN_UP = 'sign-up'
export const LOGIN = 'login'
export const LOGOUT = 'logout'

export const API_ROUTES = {
    admin: {
        dashboard: `${ADMIN_ROUTE + SLASH + DASHBOARD}`,
        analytics: `${ADMIN_ROUTE + SLASH + ANALYTICS}`,
        category: `${ADMIN_ROUTE + SLASH + CATEGORY}`,
        product: `${ADMIN_ROUTE + SLASH + PRODUCT}`,
        orders: `${ADMIN_ROUTE + SLASH + ORDERS}`,
        postCoupon: `${ADMIN_ROUTE + SLASH + POST_COUPON}`,
        coupon: `${ADMIN_ROUTE + SLASH + COUPON}`,
        faq: `${ADMIN_ROUTE + SLASH + FAQ}`,
    },
    customer: {
        dashboard: `${CUSTOMER_ROUTE + SLASH + DASHBOARD}`,
        cart: `${CUSTOMER_ROUTE + SLASH + CART}`,
        myOrders: `${CUSTOMER_ROUTE + SLASH + MY_ORDERS}`,
        whishlist: `${CUSTOMER_ROUTE + SLASH + WHISHLIST}`,
        profile: `${CUSTOMER_ROUTE + SLASH + PROFILE}`,
    },
    visitor: {
        order: `${ORDER}`,
        signUp: `${SIGN_UP}`,
        login: `${LOGIN}`,
    }
} 