--
-- PostgreSQL database dump
--

SET search_path = public, pg_catalog;


--
-- Name: tb_users; Type: TABLE; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE TABLE tb_users (
    id integer NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    name character varying NOT NULL,
    email character varying NOT NULL,
    "emailConfirmed" boolean DEFAULT false NOT NULL,
    "accountAuthorized" boolean DEFAULT false NOT NULL,
    "dateCreation" timestamp with time zone NOT NULL,
    "dateUpdate" timestamp with time zone NOT NULL,
    permission integer DEFAULT 0 NOT NULL,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tb_users OWNER TO "visionDataset";



--
-- Name: tb_permissions; Type: TABLE; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE TABLE tb_permissions (
    "userId" integer NOT NULL,
    "albumId" integer NOT NULL
);


ALTER TABLE public.tb_permissions OWNER TO "visionDataset";







--
-- Name: tb_albums; Type: TABLE; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE TABLE tb_albums (
    id integer NOT NULL,
    name character varying NOT NULL,
    "ownerId" integer NOT NULL,
    "dateCreation" timestamp with time zone NOT NULL,
    "dateUpdate" timestamp with time zone NOT NULL,
    "viewPermission" integer DEFAULT 0 NOT NULL,
    "createPermission" integer DEFAULT 0 NOT NULL,
    "parentId" integer DEFAULT 0 NOT NULL
);



ALTER TABLE public.tb_albums OWNER TO "visionDataset";

--
-- Name: tb_tags_albums; Type: TABLE; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE TABLE tb_tags_albums (
    id integer NOT NULL,
    name character varying NOT NULL,
    "albumId" integer NOT NULL
);


ALTER TABLE public.tb_tags_albums OWNER TO "visionDataset";




-- Name: tb_annotations; Type: TABLE; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE TABLE tb_annotations (
    id integer NOT NULL,
    name character varying NOT NULL,
    "imageId" integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    width integer NOT NULL,
    height integer NOT NULL
);


ALTER TABLE public.tb_annotations OWNER TO "visionDataset";


--
-- Name: tb_tags_images; Type: TABLE; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE TABLE tb_tags_images (
    id integer NOT NULL,
    name character varying NOT NULL,
    "imageId" integer NOT NULL
);


ALTER TABLE public.tb_tags_images OWNER TO "visionDataset";

--
-- Name: tb_scripts; Type: TABLE; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE TABLE tb_scripts (
    id integer NOT NULL,
    name character varying NOT NULL,
    type character varying NOT NULL,
    "imageId" integer NOT NULL
);

ALTER TABLE public.tb_scripts OWNER TO "visionDataset";

--
-- Name: tb_images; Type: TABLE; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE TABLE tb_images (
    id integer NOT NULL,
    type character varying NOT NULL,
    "albumId" integer NOT NULL,
    "ownerId" integer NOT NULL,
    "dateUpload" timestamp with time zone NOT NULL
);


ALTER TABLE public.tb_images OWNER TO "visionDataset";

--
-- Name: activateAccount(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "activateAccount"("userId" integer) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_users SET active=true WHERE id=$1$$;


ALTER FUNCTION public."activateAccount"("userId" integer) OWNER TO "visionDataset";

--
-- Name: addAlbumRestrictedPermission(integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "addAlbumRestrictedPermission"("albumId" integer, "userId" integer) RETURNS void
    LANGUAGE sql
    AS $$INSERT INTO tb_permissions("albumId", "userId") VALUES($1, $2)$$;


ALTER FUNCTION public."addAlbumRestrictedPermission"("albumId" integer, "userId" integer) OWNER TO "visionDataset";

--
-- Name: addAlbumTag(character varying, integer, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "addAlbumTag"("tagName" character varying, "albumId" integer, "dateUpdate" timestamp with time zone) RETURNS bigint
    LANGUAGE sql
    AS $$INSERT INTO tb_tags_albums(name, "albumId") VALUES($1, $2);
	 UPDATE tb_albums SET "dateUpdate"=$3 WHERE id=$2;
	 SELECT currval(pg_get_serial_sequence('tb_tags_albums', 'id'))
      $$;


ALTER FUNCTION public."addAlbumTag"("tagName" character varying, "albumId" integer, "dateUpdate" timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: addImageAnnotation(character varying, integer, integer, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "addImageAnnotation"("tagName" character varying, "imageId" integer, x integer, y integer, width integer, height integer) RETURNS bigint
    LANGUAGE sql
    AS $$INSERT INTO tb_annotations(name, "imageId", x, y, width, height) VALUES($1, $2, $3, $4, $5, $6);
SELECT currval(pg_get_serial_sequence('tb_annotations', 'id'));
$$;


ALTER FUNCTION public."addImageAnnotation"("tagName" character varying, "imageId" integer, x integer, y integer, width integer, height integer) OWNER TO "visionDataset";

--
-- Name: addImageTag(character varying, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--
---aqui

CREATE FUNCTION "addImageTag"("tagName" character varying, "imageId" integer) RETURNS bigint
    LANGUAGE sql
    AS $$INSERT INTO tb_tags_images(name, "imageId") VALUES($1, $2);
SELECT currval(pg_get_serial_sequence('tb_tags_images', 'id'));
$$;


ALTER FUNCTION public."addImageTag"("tagName" character varying, "imageId" integer) OWNER TO "visionDataset";

--
-- Name: addScript(character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--
---aqui

CREATE FUNCTION "addScript"("scriptName" character varying, "imageId" integer) RETURNS bigint
    LANGUAGE sql
    AS $$INSERT INTO tb_scripts(name, "imageId") VALUES($1, $2, $3);
SELECT currval(pg_get_serial_sequence('tb_scripts', 'id'));
$$;


ALTER FUNCTION public."addScript"("scriptName" character varying, "imageId" integer) OWNER TO "visionDataset";

--
-- Name: addImageToAlbum(character varying, integer, integer, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "addImageToAlbum"(type character varying, "albumId" integer, "ownerId" integer, "dateUpload" timestamp with time zone) RETURNS bigint
    LANGUAGE sql
    AS $$INSERT INTO tb_images(type, "albumId", "ownerId", "dateUpload") VALUES($1, $2, $3, $4);
UPDATE tb_albums SET "dateUpdate"=$4 WHERE id=$2;
SELECT currval(pg_get_serial_sequence('tb_images', 'id'));
$$;


ALTER FUNCTION public."addImageToAlbum"(type character varying, "albumId" integer, "ownerId" integer, "dateUpload" timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: authorizeAccount(character varying); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "authorizeAccount"(username character varying) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_users SET "accountAuthorized"=true WHERE username=$1$$;


ALTER FUNCTION public."authorizeAccount"(username character varying) OWNER TO "visionDataset";

--
-- Name: changeAlbumOwner(integer, integer, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "changeAlbumOwner"("albumId" integer, "userId" integer, "dateUpdate" timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_albums SET "ownerId"=$2, "dateUpdate"=$3 WHERE id=$1$$;


ALTER FUNCTION public."changeAlbumOwner"("albumId" integer, "userId" integer, "dateUpdate" timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: changePassword(character varying, character varying, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "changePassword"(username character varying, password character varying, "dateUpdate" timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_users SET password=$2, "dateUpdate"=$3 WHERE username=$1$$;


ALTER FUNCTION public."changePassword"(username character varying, password character varying, "dateUpdate" timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: confirmEmail(character varying); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "confirmEmail"(username character varying) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_users SET "emailConfirmed"=true WHERE username=$1$$;


ALTER FUNCTION public."confirmEmail"(username character varying) OWNER TO "visionDataset";

--
-- Name: createAlbum(character varying, integer, timestamp with time zone, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "createAlbum"(name character varying, "ownerId" integer, "dateCreation" timestamp with time zone, "viewPermission" integer, "createPermission" integer, "parentId" integer) RETURNS bigint
    LANGUAGE sql
    AS $$INSERT INTO tb_albums(name, "ownerId", "dateCreation", "dateUpdate","viewPermission" , "createPermission", "parentId") VALUES($1, $2, $3, $3, $4, $5, $6);
SELECT currval(pg_get_serial_sequence('tb_albums', 'id'));
$$;


ALTER FUNCTION public."createAlbum"(name character varying, "ownerId" integer, "dateCreation" timestamp with time zone, "viewPermission" integer, "createPermission" integer, "parentId" integer) OWNER TO "visionDataset";

--
-- Name: createUser(character varying, character varying, character varying, character varying, boolean, boolean, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "createUser"(username character varying, password character varying, name character varying, email character varying, "emailConfirmed" boolean, "accountAuthorized" boolean, "dateCreation" timestamp with time zone) RETURNS bigint
    LANGUAGE sql
    AS $$INSERT INTO tb_users(username, password, name, email, "emailConfirmed", "accountAuthorized", "dateCreation", "dateUpdate") VALUES($1, $2, $3, $4, $5, $6, $7, $7);
SELECT currval(pg_get_serial_sequence('tb_users', 'id'));
$$;


ALTER FUNCTION public."createUser"(username character varying, password character varying, name character varying, email character varying, "emailConfirmed" boolean, "accountAuthorized" boolean, "dateCreation" timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: deactivateAccount(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "deactivateAccount"("userId" integer) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_users SET active=false WHERE id=$1$$;


ALTER FUNCTION public."deactivateAccount"("userId" integer) OWNER TO "visionDataset";

--
-- Name: deleteAlbum(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "deleteAlbum"("albumId" integer) RETURNS void
    LANGUAGE sql
    AS $$DELETE FROM tb_albums WHERE id=$1$$;


ALTER FUNCTION public."deleteAlbum"("albumId" integer) OWNER TO "visionDataset";
---aqui
--
-- Name: deleteAlbumTag(integer, integer, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "deleteAlbumTag"("tagId" integer, "albumId" integer, date timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$DELETE FROM tb_tags_albums WHERE id=$1 AND "albumId"=$2; UPDATE tb_albums SET "dateUpdate"=$3 WHERE id=$2;$$;


ALTER FUNCTION public."deleteAlbumTag"("tagId" integer, "albumId" integer, date timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: deleteImage(integer, integer, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "deleteImage"("imageId" integer, "albumId" integer, date timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$DELETE FROM tb_images WHERE id=$1 AND "albumId"=$2; UPDATE tb_albums SET "dateUpdate"=$3 WHERE id=$2;$$;


ALTER FUNCTION public."deleteImage"("imageId" integer, "albumId" integer, date timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: deleteImageAnnotation(integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "deleteImageAnnotation"("tagId" integer, "imageId" integer) RETURNS void
    LANGUAGE sql
    AS $$DELETE FROM tb_annotations WHERE id=$1 AND "imageId"=$2$$;


ALTER FUNCTION public."deleteImageAnnotation"("tagId" integer, "imageId" integer) OWNER TO "visionDataset";

--
-- Name: deleteImageTag(integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "deleteImageTag"("tagId" integer, "imageId" integer) RETURNS void
    LANGUAGE sql
    AS $$DELETE FROM tb_tags_images WHERE id=$1 AND "imageId"=$2$$;


ALTER FUNCTION public."deleteImageTag"("tagId" integer, "imageId" integer) OWNER TO "visionDataset";



--
-- Name: getAlbum(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getAlbum"("albumId" integer) RETURNS tb_albums
    LANGUAGE sql
    AS $$SELECT * FROM tb_albums WHERE id=$1$$;


ALTER FUNCTION public."getAlbum"("albumId" integer) OWNER TO "visionDataset";

--
-- Name: getAlbumForImage(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getAlbumForImage"("imageId" integer) RETURNS tb_albums
    LANGUAGE sql
    AS $$SELECT A FROM tb_albums A, tb_images I WHERE I.id=$1 AND A.id=I."albumId"$$;


ALTER FUNCTION public."getAlbumForImage"("imageId" integer) OWNER TO "visionDataset";


--
-- Name: getAlbumTag(integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getAlbumTag"("albumId" integer, "tagId" integer) RETURNS tb_tags_albums
    LANGUAGE sql
    AS $$SELECT * FROM tb_tags_albums WHERE id=$2 AND "albumId"=$1$$;


ALTER FUNCTION public."getAlbumTag"("albumId" integer, "tagId" integer) OWNER TO "visionDataset";

--
-- Name: getAlbumTags(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getAlbumTags"("albumId" integer) RETURNS SETOF tb_tags_albums
    LANGUAGE sql
    AS $$SELECT * FROM tb_tags_albums WHERE "albumId"=$1 ORDER BY name ASC$$;


ALTER FUNCTION public."getAlbumTags"("albumId" integer) OWNER TO "visionDataset";

--
-- Name: getAlbumsOwner(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getAlbumsOwner"("ownerId" integer) RETURNS SETOF tb_albums
    LANGUAGE sql
    AS $$SELECT * FROM tb_albums WHERE "ownerId"=$1 ORDER BY "dateCreation" ASC$$;


ALTER FUNCTION public."getAlbumsOwner"("ownerId" integer) OWNER TO "visionDataset";

--
-- Name: getAlbumsUser(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getAlbumsUser"("userId" integer) RETURNS SETOF tb_albums
    LANGUAGE sql
    AS $$SELECT A FROM tb_albums A WHERE
	-- exclude sub albums
	A."parentId"=0
	AND
	(
		-- owner
		A."ownerId"=$1
		OR
		-- public/users view permission
		A."viewPermission"<=10
		OR
		-- users add permission
		A."createPermission"=0
		OR
		-- private permissions
		id IN
		(
			SELECT id
			FROM tb_albums A, tb_permissions P WHERE
				P."albumId" = A.id
				AND
				P."userId" = $1
		)
	)
ORDER BY A."dateCreation" ASC$$;


ALTER FUNCTION public."getAlbumsUser"("userId" integer) OWNER TO "visionDataset";

--
-- Name: getAlbumsUserCreate(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getAlbumsUserCreate"("userId" integer) RETURNS SETOF tb_albums
    LANGUAGE sql
    AS $$SELECT A FROM tb_albums A WHERE
	-- exclude sub albums
	A."parentId"=0
	AND
	(
		-- owner
		A."ownerId"=$1
		OR
		-- users add permission
		A."createPermission"=0
		OR
		-- private permissions
		id IN
		(
			SELECT id
			FROM tb_albums A, tb_permissions P WHERE
				P."albumId" = A.id
				AND
				P."userId" = $1
		)
	)
ORDER BY A."dateCreation" ASC$$;


ALTER FUNCTION public."getAlbumsUserCreate"("userId" integer) OWNER TO "visionDataset";

--
-- Name: getAllAlbums(); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getAllAlbums"() RETURNS SETOF tb_albums
    LANGUAGE sql
    AS $$SELECT A FROM tb_albums A WHERE
	-- exclude sub albums
	A."parentId"=0
ORDER BY "dateCreation" ASC$$;


ALTER FUNCTION public."getAllAlbums"() OWNER TO "visionDataset";



--
-- Name: getImage(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getImage"("imageId" integer) RETURNS tb_images
    LANGUAGE sql
    AS $$SELECT * FROM tb_images WHERE id=$1$$;


ALTER FUNCTION public."getImage"("imageId" integer) OWNER TO "visionDataset";

--

--
-- Name: getImageAnnotation(integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getImageAnnotation"("imageId" integer, "tagId" integer) RETURNS tb_annotations
    LANGUAGE sql
    AS $$SELECT * FROM tb_annotations WHERE id=$2 AND "imageId"=$1$$;


ALTER FUNCTION public."getImageAnnotation"("imageId" integer, "tagId" integer) OWNER TO "visionDataset";

--
-- Name: getImageAnnotations(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getImageAnnotations"("imageId" integer) RETURNS SETOF tb_annotations
    LANGUAGE sql
    AS $$SELECT * FROM tb_annotations WHERE "imageId"=$1 ORDER BY name ASC$$;


ALTER FUNCTION public."getImageAnnotations"("imageId" integer) OWNER TO "visionDataset";


--
-- Name: getImageTag(integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getImageTag"("imageId" integer, "tagId" integer) RETURNS tb_tags_images
    LANGUAGE sql
    AS $$SELECT * FROM tb_tags_images WHERE id=$2 AND "imageId"=$1$$;


ALTER FUNCTION public."getImageTag"("imageId" integer, "tagId" integer) OWNER TO "visionDataset";

--
-- Name: getImageTags(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getImageTags"("imageId" integer) RETURNS SETOF tb_tags_images
    LANGUAGE sql
    AS $$SELECT * FROM tb_tags_images WHERE "imageId"=$1 ORDER BY name ASC$$;


ALTER FUNCTION public."getImageTags"("imageId" integer) OWNER TO "visionDataset";

--
-- Name: getImagesAlbum(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getImagesAlbum"("albumId" integer) RETURNS SETOF tb_images
    LANGUAGE sql
    AS $$SELECT * FROM tb_images WHERE "albumId"=$1 ORDER BY "dateUpload" ASC$$;


ALTER FUNCTION public."getImagesAlbum"("albumId" integer) OWNER TO "visionDataset";

--
-- Name: getPublicAlbums(); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getPublicAlbums"() RETURNS SETOF tb_albums
    LANGUAGE sql
    AS $$SELECT * FROM tb_albums WHERE "viewPermission"=0 AND "parentId"=0 ORDER BY "dateCreation" ASC$$;


ALTER FUNCTION public."getPublicAlbums"() OWNER TO "visionDataset";

--
-- Name: getSubAlbums(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getSubAlbums"("albumId" integer) RETURNS SETOF tb_albums
    LANGUAGE sql
    AS $$SELECT * FROM tb_albums WHERE "parentId"=$1 ORDER BY "dateCreation" ASC$$;


ALTER FUNCTION public."getSubAlbums"("albumId" integer) OWNER TO "visionDataset";

--
-- Name: getTagsNames(); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getTagsNames"() RETURNS SETOF character varying
    LANGUAGE sql
    AS $$
SELECT name from "tb_tags_albums"
UNION
SELECT name from "tb_tags_images"
UNION
SELECT name from "tb_annotations"
$$;


ALTER FUNCTION public."getTagsNames"() OWNER TO "visionDataset";



--
-- Name: getUserById(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getUserById"(id integer) RETURNS tb_users
    LANGUAGE sql
    AS $$SELECT * FROM tb_users WHERE id = $1;$$;


ALTER FUNCTION public."getUserById"(id integer) OWNER TO "visionDataset";

--
-- Name: getUserByUsername(character varying); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getUserByUsername"(username character varying) RETURNS tb_users
    LANGUAGE sql
    AS $$SELECT * FROM tb_users WHERE username = $1;$$;


ALTER FUNCTION public."getUserByUsername"(username character varying) OWNER TO "visionDataset";

--
-- Name: getUsers(); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getUsers"() RETURNS SETOF tb_users
    LANGUAGE sql
    AS $$SELECT * FROM tb_users ORDER BY username ASC$$;


ALTER FUNCTION public."getUsers"() OWNER TO "visionDataset";

--
-- Name: getUsersExcept(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getUsersExcept"("userId" integer) RETURNS SETOF tb_users
    LANGUAGE sql
    AS $$SELECT * FROM tb_users WHERE id!=$1 ORDER BY username ASC$$;


ALTER FUNCTION public."getUsersExcept"("userId" integer) OWNER TO "visionDataset";

--
-- Name: getUsersWithPermission(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getUsersWithPermission"("albumId" integer) RETURNS SETOF tb_users
    LANGUAGE sql
    AS $$SELECT U
FROM tb_users U, tb_permissions P, tb_albums A
WHERE
	U.id = P."userId" AND
	P."albumId"=$1 AND
	P."albumId"=A.id AND
	U.id != A."ownerId"
ORDER BY U."name" ASC$$;


ALTER FUNCTION public."getUsersWithPermission"("albumId" integer) OWNER TO "visionDataset";

--
-- Name: getUsersWithoutPermission(integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "getUsersWithoutPermission"("albumId" integer) RETURNS SETOF tb_users
    LANGUAGE sql
    AS $$SELECT U
FROM tb_users U, tb_albums A
WHERE
	U.id NOT IN (SELECT id FROM "getUsersWithPermission"($1))
	AND
	U.id != A."ownerId"
	AND
	A.id = $1
ORDER BY U."name" ASC$$;


ALTER FUNCTION public."getUsersWithoutPermission"("albumId" integer) OWNER TO "visionDataset";

--
-- Name: moveAlbum(integer, integer, integer, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "moveAlbum"("albumSrcId" integer, "albumDstId" integer, "albumId" integer, date timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$
	UPDATE tb_albums SET "parentId"=$2 WHERE id=$3;
	UPDATE tb_albums SET "dateUpdate"=$4 WHERE id=$1;
	UPDATE tb_albums SET "dateUpdate"=$4 WHERE id=$2;
	UPDATE tb_albums SET "dateUpdate"=$4 WHERE id=$3;
$$;


ALTER FUNCTION public."moveAlbum"("albumSrcId" integer, "albumDstId" integer, "albumId" integer, date timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: moveImage(integer, integer, integer, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "moveImage"("albumSrcId" integer, "albumDstId" integer, "imageId" integer, date timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$
	UPDATE tb_images SET "albumId"=$2 WHERE id=$3;
	UPDATE tb_albums SET "dateUpdate"=$4 WHERE id=$1;
	UPDATE tb_albums SET "dateUpdate"=$4 WHERE id=$2;
$$;


ALTER FUNCTION public."moveImage"("albumSrcId" integer, "albumDstId" integer, "imageId" integer, date timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: removeUser(character varying); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "removeUser"(username character varying) RETURNS void
    LANGUAGE sql
    AS $$DELETE FROM tb_users WHERE username=$1$$;


ALTER FUNCTION public."removeUser"(username character varying) OWNER TO "visionDataset";

--
-- Name: renameAlbum(integer, character varying, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "renameAlbum"("albumId" integer, "albumName" character varying, date timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_albums SET name=$2, "dateUpdate"=$3 WHERE id=$1$$;


ALTER FUNCTION public."renameAlbum"("albumId" integer, "albumName" character varying, date timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: setAlbumPermissions(integer, integer, integer, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "setAlbumPermissions"("albumId" integer, "viewPermission" integer, "createPermission" integer, "dateUpdate" timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_albums SET "viewPermission"=$2, "createPermission"=$3, "dateUpdate"=$4 WHERE id=$1;
DELETE FROM tb_permissions WHERE "albumId"=$1;$$;


ALTER FUNCTION public."setAlbumPermissions"("albumId" integer, "viewPermission" integer, "createPermission" integer, "dateUpdate" timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: setUserPermission(character varying, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "setUserPermission"(username character varying, permission integer) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_users SET permission=$2 WHERE username=$1$$;


ALTER FUNCTION public."setUserPermission"(username character varying, permission integer) OWNER TO "visionDataset";

--
-- Name: updateAnnotation(integer, character varying, integer, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "updateAnnotation"("tagId" integer, "tagName" character varying, x integer, y integer, width integer, height integer) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_annotations SET name=$2, x=$3, y=$4, width=$5, height=$6 WHERE "id"=$1;
$$;


ALTER FUNCTION public."updateAnnotation"("tagId" integer, "tagName" character varying, x integer, y integer, width integer, height integer) OWNER TO "visionDataset";

--
-- Name: updateUser(integer, character varying, character varying, character varying, character varying, boolean, timestamp with time zone); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "updateUser"(id integer, username character varying, password character varying, name character varying, email character varying, "emailConfirmed" boolean, "dateUpdate" timestamp with time zone) RETURNS void
    LANGUAGE sql
    AS $$UPDATE tb_users SET username=$2, password=$3, name=$4, email=$5, "emailConfirmed"=$6, "dateUpdate"=$7 WHERE id=$1$$;


ALTER FUNCTION public."updateUser"(id integer, username character varying, password character varying, name character varying, email character varying, "emailConfirmed" boolean, "dateUpdate" timestamp with time zone) OWNER TO "visionDataset";

--
-- Name: userHasPermissionOnAlbum(integer, integer); Type: FUNCTION; Schema: public; Owner: "visionDataset"
--

CREATE FUNCTION "userHasPermissionOnAlbum"("albumId" integer, "userId" integer) RETURNS boolean
    LANGUAGE sql
    AS $$SELECT true FROM tb_permissions WHERE "albumId"=$1 AND "userId"=$2 UNION SELECT true FROM tb_albums WHERE "ownerId"=$2$$;


ALTER FUNCTION public."userHasPermissionOnAlbum"("albumId" integer, "userId" integer) OWNER TO "visionDataset";

--
-- Name: tb_albums_id_seq; Type: SEQUENCE; Schema: public; Owner: "visionDataset"
--

CREATE SEQUENCE tb_albums_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tb_albums_id_seq OWNER TO "visionDataset";

--
-- Name: tb_albums_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: "visionDataset"
--

ALTER SEQUENCE tb_albums_id_seq OWNED BY tb_albums.id;


--
-- Name: tb_annotations_id_seq; Type: SEQUENCE; Schema: public; Owner: "visionDataset"
--

CREATE SEQUENCE tb_annotations_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tb_annotations_id_seq OWNER TO "visionDataset";

--
-- Name: tb_annotations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: "visionDataset"
--

ALTER SEQUENCE tb_annotations_id_seq OWNED BY tb_annotations.id;


--
-- Name: tb_images_id_seq; Type: SEQUENCE; Schema: public; Owner: "visionDataset"
--

CREATE SEQUENCE tb_images_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tb_images_id_seq OWNER TO "visionDataset";

--
-- Name: tb_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: "visionDataset"
--

ALTER SEQUENCE tb_images_id_seq OWNED BY tb_images.id;



--
-- Name: tb_tags_albums_id_seq; Type: SEQUENCE; Schema: public; Owner: "visionDataset"
--

CREATE SEQUENCE tb_tags_albums_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tb_tags_albums_id_seq OWNER TO "visionDataset";

--
-- Name: tb_tags_albums_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: "visionDataset"
--

ALTER SEQUENCE tb_tags_albums_id_seq OWNED BY tb_tags_albums.id;


--
-- Name: tb_tags_images_id_seq; Type: SEQUENCE; Schema: public; Owner: "visionDataset"
--

CREATE SEQUENCE tb_tags_images_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tb_tags_images_id_seq OWNER TO "visionDataset";

--
-- Name: tb_tags_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: "visionDataset"
--

ALTER SEQUENCE tb_tags_images_id_seq OWNED BY tb_tags_images.id;


--
-- Name: tb_users_id_seq; Type: SEQUENCE; Schema: public; Owner: "visionDataset"
--

CREATE SEQUENCE tb_users_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tb_users_id_seq OWNER TO "visionDataset";

--
-- Name: tb_users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: "visionDataset"
--

ALTER SEQUENCE tb_users_id_seq OWNED BY tb_users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE tb_albums ALTER COLUMN id SET DEFAULT nextval('tb_albums_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE tb_annotations ALTER COLUMN id SET DEFAULT nextval('tb_annotations_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE tb_images ALTER COLUMN id SET DEFAULT nextval('tb_images_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE tb_tags_albums ALTER COLUMN id SET DEFAULT nextval('tb_tags_albums_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE tb_tags_images ALTER COLUMN id SET DEFAULT nextval('tb_tags_images_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE tb_users ALTER COLUMN id SET DEFAULT nextval('tb_users_id_seq'::regclass);


--
-- Name: pkey_tb_albuns; Type: CONSTRAINT; Schema: public; Owner: "visionDataset"; Tablespace: 
--

ALTER TABLE ONLY tb_albums
    ADD CONSTRAINT pkey_tb_albuns PRIMARY KEY (id);


--
-- Name: pkey_tb_annotations; Type: CONSTRAINT; Schema: public; Owner: "visionDataset"; Tablespace: 
--

ALTER TABLE ONLY tb_annotations
    ADD CONSTRAINT pkey_tb_annotations PRIMARY KEY (id);


--
-- Name: pkey_tb_images; Type: CONSTRAINT; Schema: public; Owner: "visionDataset"; Tablespace: 
--

ALTER TABLE ONLY tb_images
    ADD CONSTRAINT pkey_tb_images PRIMARY KEY (id);


--
-- Name: pkey_tb_tags_albums; Type: CONSTRAINT; Schema: public; Owner: "visionDataset"; Tablespace: 
--

ALTER TABLE ONLY tb_tags_albums
    ADD CONSTRAINT pkey_tb_tags_albums PRIMARY KEY (id);


--
-- Name: pkey_tb_tags_images; Type: CONSTRAINT; Schema: public; Owner: "visionDataset"; Tablespace: 
--

ALTER TABLE ONLY tb_tags_images
    ADD CONSTRAINT pkey_tb_tags_images PRIMARY KEY (id);


--
-- Name: pkey_tb_users; Type: CONSTRAINT; Schema: public; Owner: "visionDataset"; Tablespace: 
--

ALTER TABLE ONLY tb_users
    ADD CONSTRAINT pkey_tb_users PRIMARY KEY (id);


--
-- Name: username_tb_users; Type: CONSTRAINT; Schema: public; Owner: "visionDataset"; Tablespace: 
--

ALTER TABLE ONLY tb_users
    ADD CONSTRAINT username_tb_users UNIQUE (username);


--
-- Name: fki_fkey_tb_albuns_ownerId; Type: INDEX; Schema: public; Owner: "visionDataset"; Tablespace: 
--

CREATE INDEX "fki_fkey_tb_albuns_ownerId" ON tb_albums USING btree ("ownerId");


--
-- Name: fkey_tb_albuns_ownerId; Type: FK CONSTRAINT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE ONLY tb_albums
    ADD CONSTRAINT "fkey_tb_albuns_ownerId" FOREIGN KEY ("ownerId") REFERENCES tb_users(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fkey_tb_annotations_imageId; Type: FK CONSTRAINT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE ONLY tb_annotations
    ADD CONSTRAINT "fkey_tb_annotations_imageId" FOREIGN KEY ("imageId") REFERENCES tb_images(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fkey_tb_images_albumId; Type: FK CONSTRAINT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE ONLY tb_images
    ADD CONSTRAINT "fkey_tb_images_albumId" FOREIGN KEY ("albumId") REFERENCES tb_albums(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fkey_tb_images_ownerId; Type: FK CONSTRAINT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE ONLY tb_images
    ADD CONSTRAINT "fkey_tb_images_ownerId" FOREIGN KEY ("ownerId") REFERENCES tb_users(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fkey_tb_permissions_albumId; Type: FK CONSTRAINT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE ONLY tb_permissions
    ADD CONSTRAINT "fkey_tb_permissions_albumId" FOREIGN KEY ("albumId") REFERENCES tb_albums(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fkey_tb_permissions_userId; Type: FK CONSTRAINT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE ONLY tb_permissions
    ADD CONSTRAINT "fkey_tb_permissions_userId" FOREIGN KEY ("userId") REFERENCES tb_users(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fkey_tb_tags_albums_albumId; Type: FK CONSTRAINT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE ONLY tb_tags_albums
    ADD CONSTRAINT "fkey_tb_tags_albums_albumId" FOREIGN KEY ("albumId") REFERENCES tb_albums(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fkey_tb_tags_images_imageId; Type: FK CONSTRAINT; Schema: public; Owner: "visionDataset"
--

ALTER TABLE ONLY tb_tags_images
    ADD CONSTRAINT "fkey_tb_tags_images_imageId" FOREIGN KEY ("imageId") REFERENCES tb_images(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- added by rafaelLopes
CREATE TABLE tb_image_attachments
(
  id serial NOT NULL,
  "imageId" integer NOT NULL,
  "name" character varying,
  "dateCreation" timestamp with time zone NOT NULL,  
  CONSTRAINT pkey_tb_image_attachment PRIMARY KEY (id),
  CONSTRAINT fkey_img_imgttchmnt FOREIGN KEY ("imageId")
      REFERENCES tb_images (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE tb_image_attachments OWNER TO "visionDataset";    
  
    
CREATE FUNCTION "addImageAttachment"("imageId" integer, "name" character varying,  				
                                     "dateCreation" timestamp with time zone) RETURNS bigint
    LANGUAGE sql
    AS $$INSERT INTO tb_image_attachments("imageId", name, "dateCreation") VALUES($1, $2, $3);
	 SELECT currval(pg_get_serial_sequence('tb_image_attachments', 'id'));
$$;

    
CREATE FUNCTION "getImageAttachedFile"("attachedFileId" integer) RETURNS tb_image_attachments
    LANGUAGE sql
    AS $$SELECT * FROM tb_image_attachments WHERE id=$1 $$;

CREATE FUNCTION "getImageAttachments"("imageId" integer) RETURNS SETOF tb_image_attachments
    LANGUAGE sql
    AS $$SELECT * FROM tb_image_attachments WHERE "imageId"=$1 $$;

CREATE OR REPLACE FUNCTION "deleteImageAttachment"("attachmentId" integer)
  RETURNS void AS
$$DELETE FROM tb_image_attachments  WHERE id=$1$$
  LANGUAGE sql VOLATILE;

ALTER FUNCTION public."addImageAttachment"("imageId" integer, "name" character varying,"dateCreation" timestamp with time zone) OWNER TO "visionDataset";
ALTER FUNCTION public."getImageAttachedFile"("attachedFileId" integer) OWNER TO "visionDataset";
ALTER FUNCTION public."getImageAttachments"("attachedFileId" integer) OWNER TO "visionDataset";
ALTER FUNCTION "deleteImageAttachment"(integer) OWNER TO "visionDataset";
--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

