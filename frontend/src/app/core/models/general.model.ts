export interface ResponseBody<T> {
    statusCode: number;
    message: string;
    data: T | null;
    error: string | null;
}