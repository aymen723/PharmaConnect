import { getLocalAccessToken } from "@/Hooks/token";
import { STOCK_SERVICE_URL_V1 } from "../../config/settings";
import { Filter } from "../../types/requests";
import { OrderFilterParams } from "../../types/requests/paymentRequests";
import { Page } from "../../types/responses";
import { OrderRespData } from "../../types/responses/StockResponses";
import { prepareSearchParams } from "../../util/dataTransformation";
import axios, { AxiosRequestConfig } from "axios";

export const fetchOrdres = async (
  searchFilter?: Filter<OrderFilterParams>,
  config?: AxiosRequestConfig
) => {
  const params = prepareSearchParams(searchFilter);
  const token = await getLocalAccessToken();
  console.log(token, "here in the fetch orders func");
  return axios<Page<OrderRespData>>({
    url: STOCK_SERVICE_URL_V1 + `/orders`,
    headers: {
      Authorization: "Bearer " + token,
      ...config?.headers,
    },
    params,
    method: "GET",
    ...config,
  });
};

export const fetchOrderById = async (
  orderId: OrderRespData["id"],
  config?: AxiosRequestConfig
) => {
  const token = await getLocalAccessToken();

  return axios<OrderRespData>({
    url: STOCK_SERVICE_URL_V1 + `/orders/${orderId}`,
    headers: {
      Authorization: "Bearer " + token,
      ...config?.headers,
    },
    method: "GET",
    ...config,
  });
};

export const fetchOrderbysecert = async (
  secret: string,
  config?: AxiosRequestConfig
) => {
  const token = await getLocalAccessToken();
  console.log("here in the secert func", token);
  return axios<Page<OrderRespData>>({
    url: STOCK_SERVICE_URL_V1 + "/orders?orderSecret=" + secret,
    headers: {
      Authorization: "Bearer " + token,
      ...config?.headers,
    },
    method: "GET",
    ...config,
  });
};
