import { FetchAssetAPI } from "@/apis/apis";
import {
  createAsyncThunk,
  createEntityAdapter,
  createSlice,
} from "@reduxjs/toolkit";

interface Author {
  id: string;
  name: string;
  avatarPath: string;
  coverImgPath: string;
  volumne: number;
  nftsSold: number;
  followers: number;
  bio: string;
}

const AssetsAdapter = createEntityAdapter<Author>({
  sortComparer: (a, b) => a.name.localeCompare(b.name),
});

export const fetchAssets = createAsyncThunk("asset/fetchAssets", async () => {
  const data = await FetchAssetAPI(10);
  return data;
});

export const AssetSlice = createSlice({
  name: "asset",
  initialState: AssetsAdapter.getInitialState(),
  reducers: {},
  extraReducers(builder) {
    builder.addCase(fetchAssets.fulfilled, (state, action) => {
      AssetsAdapter.setAll(state, action.payload);
    });
  },
});

export default AssetSlice.reducer;
