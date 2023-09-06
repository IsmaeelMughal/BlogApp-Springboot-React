import axios from "axios";

export const BASE_URL = "http://localhost:8080";

export const myAxios = axios.create({
    baseURL: BASE_URL, // Note the correction here: Use baseURL, not BASE_URL
    withCredentials: true, // Set withCredentials to true
});
