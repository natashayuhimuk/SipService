import soap from "soap-as-promised";
import {HOST, PORT, WSDL} from '../config'
import { commonService } from "./commonService";

const url = `http://${HOST}:${PORT}${WSDL.tariff}`;

const getTariffs = async () => {
  const client = await soap.createClient(url);
  const res = await client.getTariffsAsync();
  const { response } = res[0];
  return response;
};

const addTariff = async tariff => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.addTariffAsync({ token, tariff });
  const { response } = res[0];
  return response;
};

const deleteTariffs = async tariffs => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.deleteTariffsAsync({ token, tariffs });
  const { response } = res[0];
  return response;
};

const updateTariff = async tariff => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.editTariffAsync({ token, tariff });
  const { response } = res[0];
  return response;
};

export const tariffService = {
  getTariffs,
  addTariff,
  deleteTariffs,
  updateTariff
};
