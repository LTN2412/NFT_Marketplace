// import { FetchAssetAPI } from "@/apis/query/Asset-AuthorAPI";
// import {
//   createAsyncThunk,
//   createEntityAdapter,
//   createSlice,
// } from "@reduxjs/toolkit";

// interface Asset {
//   id: string;
//   name: string;
//   description: string;
//   tags: string[];
//   price: number;
//   highestBid: number;
//   imgPath: string;
//   timeCreated: Date;
//   authorId: string;
// }

// const AssetsAdapter = createEntityAdapter<Asset>({
//   sortComparer: (a, b) => a.name.localeCompare(b.name),
// });

// export const fetchAssets = createAsyncThunk("asset/fetchAssets", async () => {
//   const data = await FetchAssetAPI(10);
//   return data;
// });

// export const AssetSlice = createSlice({
//   name: "asset",
//   initialState: AssetsAdapter.getInitialState(),
//   reducers: {},
//   extraReducers(builder) {
//     builder.addCase(fetchAssets.fulfilled, (state, action) => {
//       AssetsAdapter.setAll(state, action.payload);
//     });
//   },
// });

// export default AssetSlice.reducer;
