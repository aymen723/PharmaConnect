import axios, { AxiosRequestConfig } from "axios";
import { STOCK_SERVICE_URL_V1 } from "../../config/settings";
import {
  PharmacyRespData,
  PharmacyUptime,
  UptimeRespData,
} from "../../types/responses/StockResponses";
import { Filter } from "../../types/requests";
import { Page } from "../../types/responses";
import { prepareSearchParams } from "../../util/dataTransformation";
import {
  PharmacyFilterParams,
  PharmacyPatchRequest,
} from "../../types/requests/PharmacyRequests";
import { getLocalAccessToken } from "@/Hooks/token";

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

export const fetchPharmacyUptime = (
  pharmacyId: PharmacyRespData["id"],
  config?: AxiosRequestConfig
) => {
  return axios<PharmacyUptime>({
    url: STOCK_SERVICE_URL_V1 + `/pharmacies/${pharmacyId}/uptimes`,
    method: "GET",
    ...config,
  });
};

export const putPharmacyUptimes = (
  pharmacyId: number,
  newUptimes: Omit<UptimeRespData, "id">[]
) => {
  return axios<PharmacyUptime>({
    url: STOCK_SERVICE_URL_V1 + `/pharmacies/${pharmacyId}/uptimes`,
    method: "put",
    data: newUptimes,
    headers: {
      Authorization: "Bearer " + getLocalAccessToken(),
    },
  });
};

export const patchPharmacy = (request: PharmacyPatchRequest) => {
  return axios<PharmacyRespData>({
    url: STOCK_SERVICE_URL_V1 + `/pharmacies`,
    method: "patch",
    data: request,
    headers: {
      Authorization: "Bearer " + getLocalAccessToken(),
    },
  });
};
