import { app, BrowserWindow, ipcMain } from "electron";
import path from "path";
import type { ResizeRequest } from "./types/ipc";
import { AppDataSource, initializeDB } from "./data/connection/AppDataSource";
// Handle creating/removing shortcuts on Windows when installing/uninstalling.
if (require("electron-squirrel-startup")) {
  app.quit();
}
let isInitializingDb = false;

let mainWindow: BrowserWindow | null = null;

ipcMain.on("resize", (event, resize: ResizeRequest) => {
  console.log(resize);
  if (mainWindow) {
    console.log("changing size :::::");

    mainWindow.setSize(resize.w, resize.h);
  }
});

const createWindow = () => {
  // Create the browser window.
  const mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    resizable: false,
    webPreferences: {
      preload: path.join(__dirname, "preload.js"),
      webSecurity: true,
      nodeIntegration: true,
      contextIsolation: true,
    },
  });

  // and load the index.html of the app.
  if (MAIN_WINDOW_VITE_DEV_SERVER_URL) {
    mainWindow.loadURL(MAIN_WINDOW_VITE_DEV_SERVER_URL);
  } else {
    mainWindow.loadFile(
      path.join(__dirname, `../renderer/${MAIN_WINDOW_VITE_NAME}/index.html`)
    );
  }

  // Open the DevTools.
  mainWindow.webContents.openDevTools();

  return mainWindow;
};

const startApp = () => {
  if (!isInitializingDb && !AppDataSource.isInitialized) {
    isInitializingDb = true;
    initializeDB()
      .then((res) => {
        mainWindow = createWindow();
      })
      .finally(() => {
        isInitializingDb = false;
      });
  }
};

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on("ready", () => {
  startApp();
});

// Quit when all windows are closed, except on macOS. There, it's common
// for applications and their menu bar to stay active until the user quits
// explicitly with Cmd + Q.
app.on("window-all-closed", () => {
  if (process.platform !== "darwin") {
    app.quit();
  }
});

app.on("activate", () => {
  // On OS X it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (BrowserWindow.getAllWindows().length === 0) {
    startApp();
  }
});

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and import them here.
