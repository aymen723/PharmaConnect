import { getLocalAccessToken } from "@/Hooks/token";
import { PAYMENT_SERVICE_URL_V1 } from "../../config/settings";
import { PaymentCreationRequest } from "../../types/requests/paymentRequests";
import { OrderRespData } from "../../types/responses/StockResponses";
import { PaymentRespData } from "../../types/responses/paimentResponses";
import axios, { AxiosRequestConfig } from "axios";

export const postPayment = (
  request: PaymentCreationRequest,
  config?: AxiosRequestConfig
) => {
  return axios<PaymentRespData>({
    url: PAYMENT_SERVICE_URL_V1 + "/payment",
    headers: {
      Authorization: "Bearer " + getLocalAccessToken(),
      ...config?.headers,
    },
    data: request,
    method: "post",
    ...config,
  });
};

export const fetchPaymentById = (
  paymentId: PaymentRespData["paymentId"],
  config?: AxiosRequestConfig
) => {
  return axios<PaymentRespData>({
    url: PAYMENT_SERVICE_URL_V1 + `/payment/${paymentId}`,
    headers: {
      Authorization: "Bearer " + getLocalAccessToken(),
      ...config?.headers,
    },
    method: "GET",
    ...config,
  });
};
