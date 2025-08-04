package live.btaure.bieliauskutaure2.Helpers.Wrappers;

public class Result<T> implements IResult
{
    private T result;
    private boolean isSuccessful;

    public Result(T result, boolean successful)
    {
        this.result = result;
        this.isSuccessful = successful;
    }

    public T getResult()
    {
        return result;
    }

    public void setResult(T result)
    {
        this.result = result;
    }

    public boolean isSuccessful()
    {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful)
    {
        isSuccessful = successful;
    }
}
