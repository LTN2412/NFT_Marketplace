import BellIcon from "@/assets/Bell.svg?react";
import ListMessage from "./ListMessage/ListMessage";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { CountAllMessagesNotSeen } from "@/apis/query-options/NotificationQuery";
import { useEffect, useRef, useState } from "react";

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
    ws.current = new WebSocket(
      `http://localhost:8085/ws/notification?userId=${1}`,
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
    <div className="relative">
      <div className="cursor-pointer" onClick={handleToggleMessage}>
        <BellIcon className="w-8 fill-foreground" />
        {numberMessages && (
          <div className="absolute -right-2 -top-2 h-5 w-5 rounded-full bg-purple leading-4">
            {numberMessages}
          </div>
        )}
      </div>
      {openMessage && <ListMessage className="absolute -left-96 top-14" />}
    </div>
  );
}
