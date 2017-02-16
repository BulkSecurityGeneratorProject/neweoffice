
CREATE TABLE IF NOT EXISTS filetosend (
    id uuid,
    filename text,
    subject text,
    filetoupload blob,
    filetoupload_content_type text,
    recipientemailid text,
    recipientname text,
    addsigner text,
    addcc text,
    addbulk text,
    currentlocation text,
    destinationlocation text,
    fileinitiatedon date,
    fileclosedon date,
    dispatchno text,
    status boolean,
    PRIMARY KEY(id)
);
