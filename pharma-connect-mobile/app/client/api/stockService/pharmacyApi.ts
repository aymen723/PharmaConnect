import axios, { AxiosRequestConfig } from "axios";
import { STOCK_SERVICE_URL_V1 } from "../../config/settings";
import { PharmacyRespData } from "../../types/responses/StockResponses";
import { Filter } from "../../types/requests";
import { Page } from "../../types/responses";
import { prepareSearchParams } from "../../util/dataTransformation";
import { PharmacyFilterParams } from "../../types/requests/PharmacyRequests";

export const fetchPharmacyById = (
  pharmacyId: PharmacyRespData["id"],
  config?: AxiosRequestConfig
) => {
  return axios<PharmacyRespData>({
    url: STOCK_SERVICE_URL_V1 + `/pharmacies/${pharmacyId}`,
    method: "GET",
    ...config,
  });
};

export const fetchPharmaciesByFilter = (
  searchFilter?: Filter<PharmacyFilterParams>,
  config?: AxiosRequestConfig
) => {
  const params = prepareSearchParams(searchFilter);
  return axios<Page<PharmacyRespData>>({
    url: STOCK_SERVICE_URL_V1 + `/pharmacies`,
    params,
    method: "GET",
    ...config,
  });
};
