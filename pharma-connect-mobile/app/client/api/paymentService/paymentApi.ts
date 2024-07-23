import { getLocalAccessToken } from "@/Hooks/token";
import { PAYMENT_SERVICE_URL_V1 } from "../../config/settings";
import { PaymentCreationRequest } from "../../types/requests/paymentRequests";
import { OrderRespData } from "../../types/responses/StockResponses";
import { PaymentRespData } from "../../types/responses/paimentResponses";
import axios, { AxiosRequestConfig } from "axios";

export const postPayment = async (
  request: PaymentCreationRequest,
  config?: AxiosRequestConfig
) => {
  const token = await getLocalAccessToken();

  return axios<PaymentRespData>({
    url: PAYMENT_SERVICE_URL_V1 + "/payment",
    headers: {
      Authorization: "Bearer " + token,
      ...config?.headers,
    },
    data: request,
    method: "post",
    ...config,
  });
};

export const fetchPaymentById = async (
  paymentId: PaymentRespData["paymentId"],
  config?: AxiosRequestConfig
) => {
  const token = await getLocalAccessToken();

  return axios<PaymentRespData>({
    url: PAYMENT_SERVICE_URL_V1 + `/payment/${paymentId}`,
    headers: {
      Authorization: "Bearer " + token,
      ...config?.headers,
    },
    method: "GET",
    ...config,
  });
};
