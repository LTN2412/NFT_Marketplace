import axios from "axios";

export const getStreamAxios = () => {
  axios
    .get("http://localhost:8083/elastic/asset/page?offset=0&limit=5", {
      responseType: "stream",
      adapter: "fetch",
    })
    .then(async (res) => {
      res.data.on("data", (chunk: string) => {
        console.log(JSON.parse(chunk));
      });
    });
};

// axios
//       .get("http://localhost:8083/elastic/asset/page", {
//         params: {
//           offset: 0,
//           limit: 3,
//         },
//         responseType: "stream",
//         adapter: "fetch",
//       })
//       .then(async (res) => {
//         const reader = res.data.getReader();
//         // eslint-disable-next-line no-constant-condition
//         while (true) {
//           const { value, done } = await reader.read();
//           if (done) break;
//           console.log(JSON.parse(decoder.decode(value).substring(5)));
//         }
//       });
