#!/bin/bash


# as patient address and lattidue and longitude should be generated with different tools
# it is required to merge the data from different generator.
paste -d',' address-part1.csv on-location.csv on-part3.csv > ontario-patient.csv
paste -d',' address-part1.csv bc-location.csv bc-part3.csv > bc-patient.csv

