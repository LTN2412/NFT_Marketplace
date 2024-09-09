import { useEffect, useRef, useState } from "react";

import { CountAllMessagesNotSeen } from "@/apis/query-options/NotificationQuery";
import BellIcon from "@/assets/Bell.svg?react";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import Cookies from "js-cookie";

import ListMessage from "./ListMessage/ListMessage";

export interface BellMessageProps extends React.HTMLAttributes<HTMLDivElement> {
  numberMessage: number;
}

export default function BellMessage() {
  const queryClient = useQueryClient();
  const { data } = useQuery(CountAllMessagesNotSeen());
  const numberMessages = data?.data.result == 0 ? null : data?.data.result;
  const [openMessage, setOpenMessage] = useState(false);
  const handleToggleMessage = () => {
    setOpenMessage(!openMessage);
  };

  const ws = useRef<WebSocket | null>(null);
  useEffect(() => {
    const userId = Cookies.get("userId");
    ws.current = new WebSocket(
      `http://localhost:8085/ws/notification?userId=${userId}`,
    );
    ws.current.onopen = () => {
      console.log("WebSocket kết nối thành công");
    };
    ws.current.onmessage = (message) => {
      console.log(message);
      queryClient.invalidateQueries({
        queryKey: ["notification_not_seen"],
      });
      queryClient.invalidateQueries({
        queryKey: ["all_notification"],
      });
    };
    ws.current.onclose = () => {
      console.log("WebSocket đã ngắt kết nối");
    };
  }, [queryClient]);
  return (
    <div className="relative lg:order-2">
      <div className="cursor-pointer" onClick={handleToggleMessage}>
        <BellIcon className="w-6 fill-foreground md:w-8 lg:w-10" />
        {numberMessages && (
          <div className="absolute -right-2 -top-2 h-5 w-5 rounded-full bg-purple leading-4">
            {numberMessages}
          </div>
        )}
      </div>
      {openMessage && (
        <ListMessage className="absolute -left-96 top-14 z-40 bg-background" />
      )}
    </div>
  );
}
