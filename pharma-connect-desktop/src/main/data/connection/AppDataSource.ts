import { DataSource } from "typeorm";
import { pathFromBase } from "../../../utils/pathing";

export const AppDataSource = new DataSource({
  type: "sqlite",

  database: pathFromBase("resources", "data", "main.db"),
  synchronize: true,
  entities: ["../schema/*.js"],
});

export const initializeDB = () => {
  if (!AppDataSource.isInitialized) return AppDataSource.initialize();
  return Promise.resolve(AppDataSource);
};
