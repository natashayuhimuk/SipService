import soap from "soap-as-promised";
import {HOST, PORT, WSDL} from '../config'
import { commonService } from "./commonService";

const url = `http://${HOST}:${PORT}${WSDL.stat}`;

const getStatistics = async () => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.getStatisticsAsync({ token });
  const { response } = res[0];

  return response;
};

export const statService = {
  getStatistics
};
