# Elastic search 

## Suppose we uses elastic search for Product search service. And we have give below text for iphone 14 to elastic seach db.

15.40 cm (6.1-inch) Super Retina XDR display
Advanced camera system for better photos in any light
Cinematic mode now in 4K Dolby Vision up to 30 fps
Action mode for smooth, steady, handheld videos
Vital safety technology — Crash Detection calls for help when you can’t
All-day battery life and up to 20 hours of video playback
Industry-leading durability features with Ceramic Shield and water resistance
A15 Bionic chip with 5-core GPU for lightning-fast performance. Superfast 5G cellular
iOS 16 offers even more ways to personalise, communicate and share

How elastic search db use this information internally to store data ? 

### Ans:
Elasticsearch uses a data structure called an inverted index to store and index the textual data provided in the document. Here's how Elasticsearch would use the given information for the "iPhone 14" product to store data internally:

1. Tokenization: Elasticsearch will tokenize the text into individual terms or tokens. For example, the text "6.1-inch" will be tokenized into "6", "1", and "inch". Similarly, "Super Retina XDR display" will be tokenized into "Super", "Retina", "XDR", and "display".

2. Lowercasing: By default, Elasticsearch converts all terms to lowercase to provide case-insensitive searching. So, "Super" will be converted to "super".

3. Stop Words Removal: Elasticsearch may remove common stop words like "and", "in", "for", "with", etc., as they are usually not meaningful for search.

4. Indexing: Each term along with its associated document is stored in the inverted index. The index is organized in a way that allows fast and efficient retrieval of matching documents based on the search query terms.

5. Term Frequency (TF) and Inverse Document Frequency (IDF): Elasticsearch calculates the frequency of each term in a document (TF) and the inverse document frequency (IDF) across all documents in the index. These values are used in relevance scoring during search.

6. Relevance Scoring: When a user performs a search query, Elasticsearch uses the inverted index to find relevant documents and ranks them based on their relevance score, which is calculated using TF-IDF and other factors.

By using this inverted index structure, Elasticsearch can efficiently and quickly retrieve relevant documents based on the user's search queries. It provides powerful full-text search capabilities, making it an excellent choice for building search services like Product search service in this case.

## Elastic search installation:
- Download elastic search from 
  - https://www.elastic.co/downloads/elasticsearch
- Kibana Download: https://www.elastic.co/downloads/kibana




