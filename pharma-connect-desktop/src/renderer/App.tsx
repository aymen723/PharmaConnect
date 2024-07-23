import { Button } from "@chakra-ui/react";
import React from "react";
import {
  NavLink,
  Route,
  Routes,
  useSearchParams,
  useParams,
  useNavigation,
  useNavigate,
} from "react-router-dom";
import StoryRader from "./components/StoryReader";
import { useAppSelector } from "./redux/hooks/useAppSelector";
import { useAuthStateManager } from "./manager/global/useAuthStateManager";

const App = () => {
  const authState = useAppSelector((app) => app.auth);
  useAuthStateManager();

  React.useEffect(() => {
    console.log(`randome UUID :::: ${crypto.randomUUID()}`);
  });

  return (
    <div className="h-screen w-screen ">
      <Button
        onClick={() => {
          window.electron.ipcRenderer.channels.resize({
            h: 760,
            w: 1024,
          });
        }}
      >
        Resize
      </Button>
    </div>
  );
};

export default App;
