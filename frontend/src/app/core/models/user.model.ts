export interface UserEntity {
    id: number;
    name: string;
    email: string;
    password: string;
    role: UserRole;
    img: Uint8Array;
}

export enum UserRole {
    ADMIN = 'ADMIN',
    CUSTOMER = 'CUSTOMER'
}

export type SignUpRequest = Pick<UserEntity, 'name' | 'email' | 'password'>;
export type LoginRequest = {
    username: UserEntity['email'];
    password: UserEntity['password'];
};
export type UserLoggedIn = Pick<UserEntity, 'id' | 'role'>;
export type UserSignedUp = Pick<UserEntity, 'id' | 'name' | 'email' | 'role'>;