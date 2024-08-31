import { cn } from "@/lib/utils";
import React from "react";
import MessageCard from "./Message/Message";
import { useQuery } from "@tanstack/react-query";
import { GetAllMessagesFrom1User } from "@/apis/query-options/NotificationQuery";

export interface ListMessagesProps
  extends React.HTMLAttributes<HTMLUListElement> {}

const ListMessage = React.forwardRef<HTMLUListElement, ListMessagesProps>(
  ({ className, ...props }, ref) => {
    const { data } = useQuery(GetAllMessagesFrom1User());
    const listMessages = data?.data.result;
    return (
      <ul className={cn(className)} ref={ref} {...props}>
        {listMessages &&
          listMessages.map((message) => (
            <MessageCard key={message.id} message={message} />
          ))}
      </ul>
    );
  },
);

export default ListMessage;
