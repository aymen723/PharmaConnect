import { getLocalAccessToken } from "@/Hooks/token";
import { STOCK_SERVICE_URL_V1 } from "../../config/settings";
import { Filter } from "../../types/requests";
import {
  BookmarkCreateRequest,
  BookmarkFilterParams,
} from "../../types/requests/BookmarkRequest";
import { Page } from "../../types/responses/index";
import { BookmarkRespData } from "../../types/responses/StockResponses";
import { prepareSearchParams } from "../../util/dataTransformation";
import axios, { AxiosRequestConfig } from "axios";

export const fetchBookmarks = async (
  searchFilter?: Filter<BookmarkFilterParams>,
  config?: AxiosRequestConfig
) => {
  const params = prepareSearchParams(searchFilter);

  return axios<Page<BookmarkRespData>>({
    url: STOCK_SERVICE_URL_V1 + `/bookmarks`,
    headers: {
      Authorization: "Bearer " + getLocalAccessToken(),
      ...config?.headers,
    },
    params,
    method: "GET",
    ...config,
  });
};

export const fetchBookmarkById = (
  bookmarkId: BookmarkRespData["id"],
  config?: AxiosRequestConfig
) => {
  return axios<BookmarkRespData>({
    url: STOCK_SERVICE_URL_V1 + `/bookmarks/${bookmarkId}`,
    headers: {
      Authorization: "Bearer " + getLocalAccessToken(),
      ...config?.headers,
    },
    method: "GET",
    ...config,
  });
};

export const postBookmark = (
  request: BookmarkCreateRequest,
  config?: AxiosRequestConfig
) => {
  return axios<BookmarkRespData>({
    url: STOCK_SERVICE_URL_V1 + "/bookmarks",
    headers: {
      Authorization: "Bearer " + getLocalAccessToken(),
      ...config?.headers,
    },
    data: request,
    method: "post",
    ...config,
  });
};
