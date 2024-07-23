import React from "react";
import { useParams } from "react-router-dom";

const StoryRader = () => {
  const { storyId } = useParams();
  return <div>StoryRader : {storyId}</div>;
};

export default StoryRader;
