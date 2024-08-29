import MessageCard from "@/components/Navbar/BellMessage/ListMessage/Message/Message";
import { Message, MessageType } from "@/types/Message.type";
import { useState, useEffect, useRef } from "react";

const ReconnectingWebSocket = ({ url }) => {
  // const [newMessages, setNewMessages] = useState<number>(0);
  // const [input, setInput] = useState("");
  // const [messages, setMessages] = useState([]);

  // const ws = useRef<WebSocket | null>(null);
  // const reconnectInterval = useRef(1000); // 1 giây
  // const maxReconnectInterval = 30000; // 30 giây

  // const connect = () => {
  //   ws.current = new WebSocket("http://localhost:8085/test");

  //   ws.current.onopen = () => {
  //     console.log("WebSocket kết nối thành công");
  //     reconnectInterval.current = 1000; // Reset khi kết nối thành công
  //   };

  //   ws.current.onmessage = () => {
  //     setNewMessages((prevNewMessages) => prevNewMessages + 1); // Tăng số tin nhắn mới
  //   };

  //   ws.current.onclose = () => {
  //     console.log("WebSocket đã đóng. Thử kết nối lại sau...");
  //     attemptReconnect();
  //   };

  //   ws.current.onerror = (error) => {
  //     console.error("WebSocket lỗi:", error);
  //     ws.current ? ws.current.close() : null;
  //   };
  // };

  // const attemptReconnect = () => {
  //   setTimeout(() => {
  //     // Tăng dần thời gian kết nối lại
  //     reconnectInterval.current = Math.min(
  //       reconnectInterval.current * 2,
  //       maxReconnectInterval,
  //     );
  //     connect();
  //   }, reconnectInterval.current);
  // };

  // useEffect(() => {
  //   connect();

  //   return () => {
  //     if (ws.current) {
  //       ws.current.close();
  //     }
  //   };
  // }, [url]);

  // const sendMessage = () => {
  //   if (ws.current && ws.current.readyState === WebSocket.OPEN) {
  //     ws.current.send(input);
  //     setInput("");
  //   } else {
  //     console.error("WebSocket chưa sẵn sàng để gửi tin nhắn");
  //   }
  // };
  const sendRequestmessage: Message = {
    id: "123",
    messageType: MessageType.FRIEND_REQUEST,
    userRequestName: "LTN1",
    createdAt: new Date("2024-08-27T12:00:00Z"),
  };

  const acceptRequestmessage: Message = {
    id: "456",
    messageType: MessageType.FRIEND_ACCEPTED,
    userRequestName: "LTN2",
    createdAt: new Date("2024-08-27T12:00:00Z"),
    isSeen: false,
  };

  const haveFollowedMessage: Message = {
    id: "789",
    messageType: MessageType.HAVE_FOLLOWED,
    userRequestName: "LTN3",
    createdAt: new Date("2024-08-27T12:00:00Z"),
    isSeen: true,
  };
  const testMessage: Message = {
    id: "789",
    userRequestName: "LTN3",
    createdAt: new Date("2024-08-27T12:00:00Z"),
    isSeen: true,
  };
  return (
    // <div>
    //   <h1>Reconnecting WebSocket trong React</h1>
    //   <div>
    //     <input
    //       type="text"
    //       value={input}
    //       onChange={(e) => setInput(e.target.value)}
    //       placeholder="Nhập tin nhắn"
    //     />
    //     <button onClick={sendMessage}>Gửi</button>
    //   </div>
    //   <div>
    //     <h2>Tin nhắn:</h2>
    //     <ul>
    //       {messages.map((msg, index) => (
    //         <li key={index}>{msg}</li>
    //       ))}
    //     </ul>
    //   </div>
    // </div>
    <h1 className="h-[1000px] pt-20">
      <ul>
        <MessageCard message={sendRequestmessage}></MessageCard>
        <MessageCard message={haveFollowedMessage}></MessageCard>
        <MessageCard message={sendRequestmessage}></MessageCard>
        <MessageCard message={acceptRequestmessage}></MessageCard>
        <MessageCard message={sendRequestmessage}></MessageCard>
      </ul>
    </h1>
  );
};

export default ReconnectingWebSocket;
