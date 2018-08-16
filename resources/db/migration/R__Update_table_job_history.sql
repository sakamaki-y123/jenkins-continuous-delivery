UPDATE
   job_history 
SET
   job_folder = substr(job_name, 1, length(job_name) - length(job_base_name) - 1 ) 
WHERE
   job_folder IS NULL;

UPDATE
   job_history 
SET
   build_start_date_time = datetime(build_start_time_in_millis / 1000, 'unixepoch',"localtime")
WHERE
   build_start_date_time IS NULL;


UPDATE
   job_history 
SET
   build_finish_date_time = datetime(build_finish_time_in_millis / 1000, 'unixepoch',"localtime")
WHERE
   build_finish_date_time IS NULL;