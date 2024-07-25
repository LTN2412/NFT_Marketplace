import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { Link, useLocation } from "react-router-dom";

interface MyPagination {
  currentPage: number;
  totalElement: number;
  totalPage: number;
}

export default function MyPagination({ currentPage }: MyPagination) {
  const lessShowPage = 1;
  const moreShowPage = 5;
  const center = (moreShowPage - 1) / 2;
  const showPage = lessShowPage + moreShowPage;
  const totalPageMock = 30;
  const location = useLocation().pathname;

  const additionalPagination = [];
  for (let i = center, j = 1; i > currentPage - 1; i--, j++)
    additionalPagination.push(
      <PaginationItem>
        <Link to={`${location}?page=${currentPage + center + j}`}>
          <PaginationLink>{currentPage + center + j}</PaginationLink>
        </Link>
      </PaginationItem>,
    );
  return (
    <Pagination>
      <PaginationContent>
        <PaginationItem>
          {currentPage > 1 ? (
            <Link to={`${location}?page=${currentPage - 1}`}>
              <PaginationPrevious />
            </Link>
          ) : (
            <PaginationPrevious className="cursor-default text-gray hover:bg-background hover:text-gray md:text-clip" />
          )}
        </PaginationItem>

        {totalPageMock < showPage ? (
          Array(totalPageMock)
            .fill(0)
            .map((_, index) => (
              <PaginationItem key={index}>
                <Link to={`${location}?page=${index + 1}`}>
                  <PaginationLink isActive={currentPage == index + 1}>
                    {index + 1}
                  </PaginationLink>
                </Link>
              </PaginationItem>
            ))
        ) : moreShowPage > totalPageMock - currentPage + 1 ? (
          <>
            {Array(lessShowPage)
              .fill(0)
              .map((_, index) => (
                <PaginationItem>
                  <Link to={`${location}?page=${1 + index}`}>
                    <PaginationLink>{1 + index}</PaginationLink>
                  </Link>
                </PaginationItem>
              ))}

            <PaginationItem>
              <PaginationEllipsis />
            </PaginationItem>

            {Array(moreShowPage)
              .fill(0)
              .map((_, index) => (
                <PaginationItem>
                  <Link
                    to={`${location}?page=${totalPageMock - moreShowPage + index + 1}`}
                  >
                    <PaginationLink
                      isActive={
                        currentPage == totalPageMock - moreShowPage + index + 1
                      }
                    >
                      {totalPageMock - moreShowPage + index + 1}
                    </PaginationLink>
                  </Link>
                </PaginationItem>
              ))}
          </>
        ) : (
          <>
            {Array(moreShowPage)
              .fill(0)
              .map((_, index) => (
                <>
                  {currentPage + index - center > 0 ? (
                    <PaginationItem>
                      <Link
                        to={`${location}?page=${currentPage + index - center}`}
                      >
                        <PaginationLink
                          isActive={currentPage == currentPage + index - center}
                        >
                          {currentPage + index - center}
                        </PaginationLink>
                      </Link>
                    </PaginationItem>
                  ) : null}
                </>
              ))}

            {currentPage <= center
              ? additionalPagination.map((element) => element)
              : null}

            <PaginationItem>
              <PaginationEllipsis />
            </PaginationItem>

            {Array(lessShowPage)
              .fill(0)
              .map((_, index) => (
                <PaginationItem>
                  <Link
                    to={`${location}?page=${totalPageMock - lessShowPage + index + 1}`}
                  >
                    <PaginationLink>
                      {totalPageMock - lessShowPage + index + 1}
                    </PaginationLink>
                  </Link>
                </PaginationItem>
              ))}
          </>
        )}

        <PaginationItem>
          {currentPage != totalPageMock ? (
            <Link to={`${location}?page=${currentPage + 1}`}>
              <PaginationNext />
            </Link>
          ) : (
            <PaginationNext className="cursor-default text-gray hover:bg-background hover:text-gray" />
          )}
        </PaginationItem>
      </PaginationContent>
    </Pagination>
  );
}
