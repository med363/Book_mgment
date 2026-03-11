#!/bin/bash

BASE_URL="http://localhost:8080/api/v1/books"

echo "Testing Book Management API..."

# 1. Create a Book
TIMESTAMP=$(date +%s)
RANDOM_ISBN="9780$(shuf -i 100000000-999999999 -n 1)"

echo -e "\n1. Creating a book..."
response=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d "{
    \"title\": \"Clean Code $TIMESTAMP\",
    \"author\": \"Robert C. Martin\",
    \"isbn\": \"$RANDOM_ISBN\",
    \"publicationYear\": 2008,
    \"publisher\": \"Prentice Hall\",
    \"price\": 30.0,
    \"totalPages\": 464,
    \"description\": \"A Handbook of Agile Software Craftsmanship\"
  }")
echo "Response: $response"

# Extract ID using grep/sed
BOOK_ID=$(echo $response | grep -o '"id":[0-9]*' | grep -o '[0-9]*')
if [ -z "$BOOK_ID" ]; then
    echo -e "\nFailed to create book or extract ID! Using fallback ID 1."
    BOOK_ID=1
else
    echo -e "\nBook created successfully with ID: $BOOK_ID"
fi

# 2. Get All Books
echo -e "\n2. Getting all books..."
curl -s $BASE_URL | head -c 500 # Truncate output
echo "..."

# 3. Get Book By ID
echo -e "\n3. Getting book with ID $BOOK_ID..."
curl -s $BASE_URL/$BOOK_ID
echo ""

# 4. Update Book
echo -e "\n4. Updating book with ID $BOOK_ID..."
curl -s -X PUT $BASE_URL/$BOOK_ID \
  -H "Content-Type: application/json" \
  -d "{
    \"title\": \"Clean Code (Updated)\",
    \"author\": \"Robert C. Martin\",
    \"isbn\": \"$RANDOM_ISBN\",
    \"publicationYear\": 2008,
    \"publisher\": \"Prentice Hall\",
    \"price\": 35.0,
    \"totalPages\": 464,
    \"description\": \"Updated Description\"
  }"
echo ""

# 5. Delete Book
echo -e "\n5. Deleting book with ID $BOOK_ID..."
curl -s -X DELETE $BASE_URL/$BOOK_ID -v 2>&1 | grep "< HTTP"
echo "Deleted."

echo -e "\nUnknown book check (should start with error or empty):"
curl -s $BASE_URL/$BOOK_ID
echo -e "\nDone."
