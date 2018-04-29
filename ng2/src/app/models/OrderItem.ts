import { SearchQuery } from "./SearchQuery";

export interface OrderItem {
  orderId: number,
  dateTime: string,
  paymentid: string,
  total: number,
  item: SearchQuery[]
}
